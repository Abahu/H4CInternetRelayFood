package h4cirf.com.h4cinternetrelayfood.models;


import java.util.Date;
import java.util.List;

public class PostModel {
    public String _id;
    public Date creationDate;
    public String email;
    public String title;
    public String status;
    public List<String> eligibility;
    public String amount;
    public String readiness;
    public String description;
    public String pickupWindow;
    public String pickupAddress;
}
