package PrismaMod.actions.sfx.IllyaSFX.CardSFX.InstallSFX;

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

public class InstallBaseSFX extends AbstractSFX {
    //private final Texture img;// = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));
    private float x = 0;
    private float y = 0;
    private float vX = 0;

    private int numImages = 10;
    private Texture[] images;
    private float[] xVals = new float[numImages];
    private float[] yVals = new float[numImages];

    private static final float FADE_IN_TIME = 0.05F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private AbstractCreature target;

    public InstallBaseSFX(AbstractCreature target){
        this.images = new Texture[numImages];
        this.images[0] = TextureLoader.getTexture(makeSFXPath("circle.png"));
        this.images[1] = TextureLoader.getTexture(makeSFXPath("circle.png"));
        this.images[2] = TextureLoader.getTexture(makeSFXPath("circle.png"));
        this.images[3] = TextureLoader.getTexture(makeSFXPath("circle.png"));
        this.images[4] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[5] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[6] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[7] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[8] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        this.images[9] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        for(int i = 0; i < numImages; i++) {
            xVals[i] = (float) Settings.WIDTH * 0.3F - (float)this.images[i].getWidth();
            yVals[i] = AbstractDungeon.floorY + 100.0F * Settings.scale - (float)this.images[i].getHeight();
        }
        //this.img = TextureLoader.getTexture(makeSFXPath("DevilHorns.png"));// 23
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 24
        //this.x = (float) Settings.WIDTH * 0.3F - (float)this.img.getWidth() / 2.0F;// 25
        //this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - (float)this.img.getHeight() / 2.0F;// 26
        this.vX = 100.0F * Settings.scale;// 27
        this.stallTimer = 1;//MathUtils.random(0.0F, 0.2F);// 28
        this.endStall = 0.5F;
        this.scale = 1.2F * Settings.scale;// 29
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

                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }

                this.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);// 68
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
        sb.setColor(this.color);
        float num = 0;
        float num2 = 0;
        for(Texture img : images) {
            sb.setColor(MathUtils.random(0.5F,1),MathUtils.random(0.5F,1),MathUtils.random(0.5F,1),0 );
            num = MathUtils.random(-2, 2);
            num2 = MathUtils.random(-2, 2);
            sb.draw(img, this.target.hb.cX - (img.getWidth())*scale*num, this.target.hb.cY/2F - (img.getHeight()*scale*num2), img.getWidth() / 2F, img.getHeight() / 2F, img.getWidth(), img.getHeight(), scale, scale, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
        }
    }

    @Override
    public void dispose() {

    }
}
