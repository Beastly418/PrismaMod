package PrismaMod.characters.Animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public abstract class AbstractPrismaAnimation implements IAnimation {
    protected float timer;
    protected float fps;
    protected int frame;
    protected boolean loop;
    public int x;
    public int y;
    public float scalex;
    public float scaley;

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    //This sub class is for delivering a sprite sheet that is split up for the animation to draw
    public class SpriteSheet {
        //width and height are the size of the sprite
        //x and y are location on the screen
        public int width, height, x, y;
        public int numX, numY;
        //file location of the whole sprite sheet
        private String spriteSheet;
        //split up animation
        private ArrayList<TextureRegion> anim = new ArrayList<>(40);

        public SpriteSheet(String spriteSheet) {
            this(spriteSheet, 100, 100);
        }

        public SpriteSheet(String spriteSheet, int numX, int numY) {
            this.numX = numX;
            this.numY = numY;
            this.split(spriteSheet);
        }

        private void split(String spriteSheet) {
            TextureRegion wholeSheet = new TextureRegion(new Texture(spriteSheet));
            this.width = wholeSheet.getRegionWidth()/this.numX;
            this.height = wholeSheet.getRegionHeight()/this.numY;
            TextureRegion[][] splitSheet = wholeSheet.split(this.width, this.height);
            for(TextureRegion[] a : splitSheet) {
                for(TextureRegion b : a) {
                    this.anim.add(b);
                }
            }
        }

        public ArrayList<TextureRegion> getAnim(){
            return this.anim;
        }

        public void setAnim(ArrayList<TextureRegion> anim) {
            this.anim = anim;
        }
    }
}
