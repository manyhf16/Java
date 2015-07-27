package zpark.struts2.action.day7;

import zpark.util.ImageUtil;

public class Base64ImageAction {

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void execute() {
        //System.out.println(image);
        ImageUtil.saveBase64Image(image);
    }

}
