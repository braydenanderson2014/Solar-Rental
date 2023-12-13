package PointofSale;

import assets.CustomScanner;
import assets.Logo;

import messageHandler.ConsoleHandler;
import messageHandler.MessageProcessor;

public class DiscountManager {
	static double itemDiscountTotal;
	static double invoiceDiscountTotal;
	static PointOfSaleManager Manager = new PointOfSaleManager();
	static DiscountManager Discount = new DiscountManager();
	public void DiscountMenu() {
		Logo.displayLogo();
		System.out.println("Discount Menu");
		if (invoiceDiscountTotal == 0) {
			System.out.println("[APP]: Apply Discount to Item");
			System.out.println("[REM]: Remove Discount from item");
		}
		System.out.println("[APPI]: Apply Item to Invoice");
		System.out.println("[REMI]: Remove Discount from invoice");
		System.out.println("[RET]: Return to POS Menu");
		ConsoleHandler.getConsole();
		String option = CustomScanner.nextLine().toLowerCase();
		if (option.equals("app")) {
			appDis();
		} else if (option.equals("rem")) {
			remDis();
		} else if (option.equals("appi")) {
			appIDis();
		} else if (option.equals("remi")) {
			remIDis();
		} else if (option.equals("ret")) {
			POSMenu.PointofSaleMenu();
		} else {
			MessageProcessor.processMessage(-1, "Invalid option, Try again.", true);
			DiscountMenu();
		}
	}

	private void remIDis() {
		Logo.displayLogo();
		System.out.println("Remove Item Discount");
		System.out.println("Enter Item Number");
		ConsoleHandler.getConsole();
		String itemNumber = CustomScanner.nextLine();
		if (itemNumber.equals("back")) {
			DiscountMenu();
		} else {
			try {
				int itemNumberAsInt = Integer.parseInt(itemNumber);
				//POSItem item = new POSItem()
				POSItem item = Manager.getItem(itemNumberAsInt);
				if (item == null) {
					MessageProcessor.processMessage(-1, "Item not found", true);
					remIDis();
				} else {
					if (item.isDiscounted()) {
						item.setDiscounted(false);
						itemDiscountTotal -= item.getCurrentPrice() - item.getOriginalPrice();
						MessageProcessor.processMessage(1, "Discount removed from item", true);
						DiscountMenu();
					} else {
						MessageProcessor.processMessage(-1, "Item is not discounted", true);
						remIDis();
					}
				}
			} catch (NumberFormatException e) {
				MessageProcessor.processMessage(-1, "Invalid Item Number", true);
				remIDis();
			}
		}
	}

	private static void appIDis() {
		Logo.displayLogo();
		System.out.println("Apply Item Discount");
		System.out.println("Enter Item Number");
		ConsoleHandler.getConsole();
		String itemNumber = CustomScanner.nextLine();
		if(itemNumber.equals("back")) {
			Discount.DiscountMenu();
		}
		try {
			int itemNum = Integer.parseInt(itemNumber);
			System.out.println("Enter Discount Amount or type % for percentage");
			ConsoleHandler.getConsole();
			String discount = CustomScanner.nextLine();
			if (discount.equals("back")) {
				appIDis();
			}
			if(discount.contains("%")) {
                discount = discount.replace("%", "");
                try {
                    double discountAsDouble = Double.parseDouble(discount);
                    double discountAmount = Manager.applyDiscountToItem(itemNum, discountAsDouble, true);
                    itemDiscountTotal += discountAmount;
                    MessageProcessor.processMessage(1, "Discount Applied to Item", true);
                    Discount.DiscountMenu();
                } catch (NumberFormatException e) {
                    MessageProcessor.processMessage(-1, "Invalid Discount Amount", true);
                    appIDis();
                }
			} else {
				try {
					double discountAsDouble = Double.parseDouble(discount);
					double discountAmount = Manager.applyDiscountToItem(itemNum, discountAsDouble, false);
					itemDiscountTotal += discountAmount;
					MessageProcessor.processMessage(1, "Discount Applied to Item", true);
					Discount.DiscountMenu();
				} catch (NumberFormatException e) {
					MessageProcessor.processMessage(-1, "Invalid Discount Amount", true);
					appIDis();
				}
			}
		} catch (NumberFormatException e) {
			MessageProcessor.processMessage(-1, "Invalid Item Number", true);
			appIDis();
		}
	}

	private static void remDis() {
	}

	private static void appDis() {
	}

}
