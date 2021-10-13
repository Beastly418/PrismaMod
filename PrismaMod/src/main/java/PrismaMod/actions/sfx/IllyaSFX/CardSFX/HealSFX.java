package PrismaMod.actions.sfx.IllyaSFX.CardSFX;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import static PrismaMod.DefaultMod.makeSFXPath;

public class HealSFX extends AbstractSFX {
    //private final Texture img;// = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));
    private float x = 0;
    private float y = 0;
    private float vX = 0;

    private int numStars = 15;
    private int numImages = 1;
    private Texture[] images;
    private float[] xVals = new float[numStars];
    private float[] yVals = new float[numStars];

    private static final float FADE_IN_TIME = 0.05F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private Color[] colors = new Color[numStars];

    private AbstractCreature target;

    public HealSFX(AbstractCreature target){
        this.images = new Texture[numImages];
        this.images[0] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        for(int i = 0; i < 15; i++) {
            colors[i] = new Color(0, MathUtils.random(0.5F, 1F), 0, 0);
            xVals[i] = MathUtils.random(target.hb.cX - images[0].getWidth()/2F, target.hb.cX + images[0].getWidth()/2F);
            yVals[i] = MathUtils.random(target.hb.cY - images[0].getHeight()/2F, target.hb.cX + images[0].getHeight()/2F);
        }
        //this.img = TextureLoader.getTexture(makeSFXPath("DevilHorns.png"));// 23
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 24
        //this.x = (float) Settings.WIDTH * 0.3F - (float)this.img.getWidth() / 2.0F;// 25
        //this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - (float)this.img.getHeight() / 2.0F;// 26
        this.vX = 100.0F * Settings.scale;// 27
        this.stallTimer = 1;//MathUtils.random(0.0F, 0.2F);// 28
        this.endStall = 0.5F;
        this.scale = 0.1F * Settings.scale;// 29
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        this.target = target;
        this.rotation = MathUtils.random(-5.0F, 1.0F);// 30
    }

    @Override
    public void update() {

        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
        } else {
            //Fade in and fade out
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58
                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }
                for (Color apple : colors) {
                    apple.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
                }
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }
                for (Color apple : colors) {
                    apple.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
                }
            } else {
                this.isDone = true;// 70
            }

        }
    }

    public float getStallTimer(){
        return stallTimer;
    }

    public float getEndStall(){
        return endStall;
    }


    @Override
    public void render(SpriteBatch sb) {
        //sb.setColor(this.color);
        for (int i = 0; i < 15; i++) {
            sb.setColor(colors[i]);
            sb.draw(images[0], xVals[i], yVals[i], images[0].getWidth()/2F, images[0].getHeight()/2F, images[0].getWidth(), images[0].getHeight(), scale, scale, rotation, 0, 0, images[0].getWidth(), images[0].getHeight(), false, false);

        }
    }

    @Override
    public void dispose() {

    }
}
