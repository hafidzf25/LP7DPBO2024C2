import java.awt.*;

public class Pipe {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;
    boolean passed = false;
    private String status;

    public Pipe(int posX, int posY, int width, int height, Image image, String status) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.status = status;

        this.velocityX = -3;
        this.passed = false;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(java.awt.Image image) {
        this.image = image;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
