package PointofSale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

import MainSystem.SettingsController;
public class PointOfSaleManager {
	private Map<POSItem, Integer> items; // Map of QTY to POSItem
	
	public PointOfSaleManager() {
        items = new HashMap<>(); // Initialize the map
    }
	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	public int size() {
		if (items.size() == 0) {
			return 0;
		}
		return items.size();
	}

	/**
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return items.containsKey(key);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return items.containsValue(value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Integer get(Object key) {
		return items.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Integer put(POSItem key, Integer value) {
		return items.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Integer remove(Object key) {
		return items.remove(key);
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends POSItem, ? extends Integer> m) {
		items.putAll(m);
	}

	/**
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		items.clear();
	}

	/**
	 * @return
	 * @see java.util.Map#keySet()
	 */
	public Set<POSItem> keySet() {
		return items.keySet();
	}

	/**
	 * @return
	 * @see java.util.Map#values()
	 */
	public Collection<Integer> values() {
		return items.values();
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<POSItem, Integer>> entrySet() {
		return items.entrySet();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return items.equals(o);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	public int hashCode() {
		return items.hashCode();
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see java.util.Map#getOrDefault(java.lang.Object, java.lang.Object)
	 */
	public Integer getOrDefault(Object key, Integer defaultValue) {
		return items.getOrDefault(key, defaultValue);
	}

	/**
	 * @param action
	 * @see java.util.Map#forEach(java.util.function.BiConsumer)
	 */
	public void forEach(BiConsumer<? super POSItem, ? super Integer> action) {
		items.forEach(action);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	public Integer putIfAbsent(POSItem key, Integer value) {
		return items.putIfAbsent(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#remove(java.lang.Object, java.lang.Object)
	 */
	public boolean remove(Object key, Object value) {
		return items.remove(key, value);
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return
	 * @see java.util.Map#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean replace(POSItem key, Integer oldValue, Integer newValue) {
		return items.replace(key, oldValue, newValue);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#replace(java.lang.Object, java.lang.Object)
	 */
	public Integer replace(POSItem key, Integer value) {
		return items.replace(key, value);
	}

	public void addItem(POSItem item, int quantity) {
		items.put(item, quantity);
	}
	
	public void removeItem(POSItem item) {
		items.remove(item);
	}
	
	public POSItem getItem(int itemNumber) {
		for (POSItem item : items.keySet()) {
			if (item.getItemNumber() == itemNumber) {
				return item;
			}
		}
		return null;
	}
	
	public List<POSItem> getItems() {
		return items.keySet().stream().toList();
	}
	
	public void setItems(Map<POSItem, Integer>items) {
		this.items.putAll(items);
	}
	
	public void clearItems() {
		items.clear();
	}
	
	public double getTotal() {
		return getSubtotal() + getTax();
	}
	
	public double calculateChange(double amountPaid) {
		return amountPaid - getTotal();
	}
	
	public double applyInvoiceDiscount(double discount) {
		double totalDiscount = 0;
		for (POSItem item : items.keySet()) {
			if(item.isCanBeDiscounted()) {
				if (!item.isDiscounted()) {
					item.setDiscounted(true);
				}
			}
			if (item.isDiscounted()) {
				double itemDiscount = item.getOriginalPrice() - item.getCurrentPrice();
				double itemDiscountPercent = itemDiscount / item.getOriginalPrice();
				double itemDiscountAmount = itemDiscountPercent * discount;
				item.setCurrentPrice(item.getCurrentPrice() + itemDiscountAmount);
				totalDiscount += itemDiscountAmount;
			}
		}
		return totalDiscount;
	}

	public double applyDiscountToItem(int itemNumber, double discount) {
		POSItem item = getItem(itemNumber);
		if (item != null) {
			if (!item.isDiscounted()) {
				item.setDiscounted(true);
			}
			double itemDiscount = item.getOriginalPrice() - item.getCurrentPrice();
			double itemDiscountPercent = itemDiscount / item.getOriginalPrice();
			double itemDiscountAmount = itemDiscountPercent * discount;
			item.setCurrentPrice(item.getCurrentPrice() + itemDiscountAmount);
			return itemDiscountAmount;
		}
		return 0;
	}
	
	public double applyDiscountToItem(int itemNumber, double discount, boolean isPercentage) {
		POSItem item = getItem(itemNumber);
		if (item != null) {
			if (!item.isDiscounted()) {
				item.setDiscounted(true);
			}
			double itemDiscount = item.getOriginalPrice() - item.getCurrentPrice();
			double itemDiscountAmount = 0;
			if (isPercentage) {
				itemDiscountAmount = itemDiscount * (discount / 100);
			} else {
				itemDiscountAmount = discount;
			}
			item.setCurrentPrice(item.getCurrentPrice() + itemDiscountAmount);
			return itemDiscountAmount;
		}
		return 0;
	}
	
	public double removeDiscoune(int itemNumber) {
		POSItem item = getItem(itemNumber);
		if (item != null) {
			if (item.isDiscounted()) {
				item.setDiscounted(false);
				item.setCurrentPrice(item.getOriginalPrice());
				return item.getOriginalPrice() - item.getCurrentPrice();
			}
		}
		return 0;
	}
	
	public double calculateDiscount() {
		double discount = 0;
		for (POSItem item : items.keySet()) {
			if (item.isDiscounted()) {
				discount += item.getOriginalPrice() - item.getCurrentPrice();
			}
		}
		return discount;
	}
	
	public double getSubtotal() {
		double subtotal = 0;
		for (POSItem item : items.keySet()) {
			subtotal += item.getCurrentPrice();
		}
		return subtotal;
	}
	
	public double getTax() {
		Double taxRate = Double.parseDouble(SettingsController.getSetting("TaxP"));
		taxRate = taxRate / 100;
		return Math.round(getSubtotal() * taxRate * 100.0) / 100.0;
	}
}
