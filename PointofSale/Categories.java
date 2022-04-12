package PointofSale;


public class Categories {
    private String item;
    private String Category;
    private double price;
    private String description;
    private int itemID;
    public Categories(String item, String Category, double price, String description, int itemID){
       this.item = item;
       this.Category = Category;
       this.price = price;
       this.description = description;
       this.itemID = itemID;
   }
   public static void serializeToXML(){
       //XMLMapper xmlMapper = new XMLMapper();
   }
}
