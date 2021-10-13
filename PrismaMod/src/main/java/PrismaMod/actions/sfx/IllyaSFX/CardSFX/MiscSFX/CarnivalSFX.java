package PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX;

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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.makeSFXPath;

public class CarnivalSFX extends AbstractSFX {
    //private final Texture img;// = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));
    private float x = 0;
    private float y = 0;
    private float vX = 0;

    private int numImages = 9;
    private Texture[] images;
    private float[] xVals = new float[numImages];
    private float[] yVals = new float[numImages];

    private static final float FADE_IN_TIME = 0.05F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private Color[] colors = new Color[numImages];

    private AbstractCreature target;

    public CarnivalSFX(AbstractCreature target){
        this.images = new Texture[numImages];
        this.images[0] = TextureLoader.getTexture(makeSFXPath("circle.png"));
        this.images[1] = TextureLoader.getTexture(makeSFXPath("Hexagon.png"));
        this.images[2] = TextureLoader.getTexture(makeSFXPath("Pentagon.png"));
        this.images[3] = TextureLoader.getTexture(makeSFXPath("Triangle.png"));
        this.images[4] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[5] = TextureLoader.getTexture(makeSFXPath("SpadeSpecial.png"));
        this.images[6] = TextureLoader.getTexture(makeSFXPath("ClubSpecial.png"));
        this.images[7] = TextureLoader.getTexture(makeSFXPath("HeartSpecial.png"));
        this.images[8] = TextureLoader.getTexture(makeSFXPath("DiamondSpecial.png"));
        for(int i = 0; i < numImages; i++) {
            colors[i] = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 0);
            xVals[i] = MathUtils.random(30, Settings.WIDTH);
            yVals[i] = MathUtils.random(AbstractDungeon.floorY, target.dialogY);
        }
        //this.img = TextureLoader.getTexture(makeSFXPath("DevilHorns.png"));// 23
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 24
        //this.x = (float) Settings.WIDTH * 0.3F - (float)this.img.getWidth() / 2.0F;// 25
        //this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - (float)this.img.getHeight() / 2.0F;// 26
        this.vX = 100.0F * Settings.scale;// 27
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        this.scale = 0.25F * Settings.scale;// 29
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

    @Override
    public void render(SpriteBatch sb) {
        //sb.setColor(this.color);
        for (int i = 0; i < numImages; i++) {
            sb.setColor(colors[i]);
            sb.draw(images[i], xVals[i], yVals[i], images[i].getWidth()/2F, images[i].getHeight()/2F, images[i].getWidth(), images[i].getHeight(), scale, scale, rotation, 0, 0, images[i].getWidth(), images[i].getHeight(), false, false);

        }
    }

    public float getStallTimer(){
        return stallTimer;
    }

    public float getEndStall(){
        return endStall;
    }

    @Override
    public void dispose() {

    }
}
