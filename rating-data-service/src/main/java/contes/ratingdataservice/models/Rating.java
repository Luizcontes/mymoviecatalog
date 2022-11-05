package contes.ratingdataservice.models;

public class Rating {
    
    private String moviedId;
    private int rating;
    
    public Rating(String moviedId, int rating) {
        this.moviedId = moviedId;
        this.rating = rating;
    }

    public String getMoviedId() {
        return moviedId;
    }

    public void setMoviedId(String moviedId) {
        this.moviedId = moviedId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
}
