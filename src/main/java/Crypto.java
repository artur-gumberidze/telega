public class Crypto {
    int id;
    String title;
    Double usdprice;
    public Crypto(int id, String title, Double usdprice) {
        this.id = id;
        this.title = title;
        this.usdprice = usdprice;
    }

    @Override
    public String toString() {
        return "Crypto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", usdprice=" + usdprice +
                '}';
    }

}
