package PrismaMod.actions.MonsterActions;

import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static PrismaMod.DefaultMod.makeMonsterSFXPath;

public abstract class AbstractMonsterSFX extends AbstractGameEffect {

    public ArrayList<picture> images;
    public boolean playedSound = false;
    public boolean playedVoice = false;
    protected float stallTimer = 1.15F;
    protected float endStall = stallTimer/2F;
    protected float scale;
    protected float rotation;
    protected float fadeInTimer;
    protected float fadeOutTimer;

    protected class picture {
        public Texture image;
        public float x;
        public float y;
        public Color color;
        public float rotation;
        public float scale;
        public picture(Texture image, float x, float y, Color color, float scale, float rotation){
            this.image = image;
            this.x = x;
            this.y = y;
            this.color = color;
            this.scale = scale;
            this.rotation = 0;
        }

        public picture(Texture image, float x, float y, Color color){
            this(image, x, y, color, 0, 0);
        }

        public picture(String imagePath){
            this(imagePath, 0, 0, new Color(1,1,1,1));
        }

        public picture(String imagePath, float x, float y, Color color){
            this(TextureLoader.getTexture(imagePath), x, y, color);//makeMonsterSFXPath(imagePath)), x, y, color);
        }
    }

    public abstract float getStallTimer();
    public abstract float getEndStall();
}
