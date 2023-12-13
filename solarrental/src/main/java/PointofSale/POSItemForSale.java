package PointofSale;

public class POSItemForSale {
	private int itemNumber;
	private String itemName;
	private double originalPrice;
	private double currentPrice;
	private boolean isDiscounted;
	private boolean canBeDiscounted;
	private boolean isItemReturnable;
	private boolean isTaxExempt;
	private boolean isItemSellable;
	private int categoryID;
	private int quantity;

	public POSItemForSale(int itemNumber, String itemName, double originalPrice, double currentPrice,
			boolean isDiscounted, boolean canBeDiscounted, boolean isItemReturnable, boolean isTaxExempt,
			boolean isItemSellable, int categoryID) {
		super();
		this.itemNumber = itemNumber;
		this.itemName = itemName;
		this.originalPrice = originalPrice;
		this.currentPrice = currentPrice;
		this.isDiscounted = isDiscounted;
		this.canBeDiscounted = canBeDiscounted;
		this.isItemReturnable = isItemReturnable;
		this.isTaxExempt = isTaxExempt;
		this.isItemSellable = isItemSellable;
		this.categoryID = categoryID;
	}

	public POSItemForSale() {
		super();
		this.itemNumber = 0;
		this.itemName = "";
		this.originalPrice = 0;
		this.currentPrice = 0;
		this.isDiscounted = false;
		this.canBeDiscounted = false;
		this.isItemReturnable = false;
		this.isTaxExempt = false;
		this.isItemSellable = false;
		this.categoryID = 0;
	}
	
	public POSItemForSale fromPOSItem(POSItem item, int quantity) {
		this.itemNumber = item.getItemNumber();
		this.itemName = item.getItemName();
		this.originalPrice = item.getOriginalPrice();
		this.currentPrice = item.getCurrentPrice();
		this.isDiscounted = item.isDiscounted();
		this.canBeDiscounted = item.isCanBeDiscounted();
		this.isItemReturnable = item.isItemReturnable();
		this.isTaxExempt = item.isTaxExempt();
		this.isItemSellable = item.isItemSellable();
		this.categoryID = item.getCategoryID();
		this.quantity = quantity;
		return this;
	}
	
	

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName.trim();
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public boolean isDiscounted() {
		return isDiscounted;
	}

	public void setDiscounted(boolean isDiscounted) {
		this.isDiscounted = isDiscounted;
	}

	public boolean isCanBeDiscounted() {
		return canBeDiscounted;
	}
	
}
