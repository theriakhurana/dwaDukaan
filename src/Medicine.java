public class Medicine{
  private int id;
  private String name;
  private int stock;
  private String expiryDate; // YYYY-MM-DD
  private String manufacturingDate; // YYYY-MM-DD
  private String manufacturer;
  private double price;


  public Medicine(int id, String name, int stock, String expiryDate, String manufacturingDate, String manufacturer, double price) {
    this.id = id;
    this.name = name;
    this.stock = stock;
    this.expiryDate = expiryDate;
    this.price = price;
    //this.manufacturingDate = manufacturingDate;
    //this.manufacturer = manufacturer;
  }

// getters
  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public int getStock(){
    return stock;
  }

  public String getExpiryDate(){
    return expiryDate;
  }

  public double getPrice(){
    return price;
  }

//setters
  public void setName(String name){
    this.name = name;
  }

  public void setStock(int stock){
    this.stock = stock;
  }

  public void setExpiryDate(String expDate){
    this.expiryDate = expDate;
  }

  public void setPrice(double price){
    this.price = price;
  }

  @Override
  public String toString(){
    return String.format("ID: %d | Name: %s | Stock: %d | Expiry: %s | Price: %.2f", id, name, stock, expiryDate, price);
  }
};