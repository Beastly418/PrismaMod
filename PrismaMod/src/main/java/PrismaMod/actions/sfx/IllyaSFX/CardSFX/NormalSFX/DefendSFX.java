package PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.getModID;
import static PrismaMod.DefaultMod.makeSFXPath;

public class DefendSFX extends AbstractSFX {

    private int numStars = 15;
    private int numImages = 1;
    private Texture[] images;
    private float[] xVals = new float[numStars];
    private float[] yVals = new float[numStars];

    private static final float FADE_IN_TIME = 0.5F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private Color[] colors = new Color[numStars];

    private AbstractCreature target;

    public DefendSFX(AbstractCreature target){
        this.images = new Texture[numImages];
        this.images[0] = TextureLoader.getTexture(makeSFXPath("Sparkle.png"));
        //this.img = TextureLoader.getTexture(makeSFXPath("DevilHorns.png"));// 23
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 24
        this.scale = 0.1F * Settings.scale;// 29
        this.target = target;
        this.rotation = MathUtils.random(-5.0F, 1.0F);// 30
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
    }

    @Override
    public void update() {

        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
            if (this.stallTimer < endStall && !playedVoice){
                double rand = Math.random()*10;
                if (rand < 6) {
                    CardCrawlGame.sound.play(getModID() + "SKILL_1");
                } else {
                    CardCrawlGame.sound.play(getModID() + "SKILL_2");
                }
                playedVoice = true;
            }
            if(!playedSound) {
                CardCrawlGame.sound.play(getModID() + "SE_2");
                playedSound = true;
            }
        } else {

            //Fade in and fade out
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58

                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }

            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
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
        if (this.stallTimer <= endStall) {
            for (int i = 0; i < 2; i++) {
                float rand = (float) Math.random();
                float rand2 = (float) Math.random()*.5F;
                sb.setColor(new Color(rand2, rand2, rand, 1));
                sb.draw(images[0], AbstractDungeon.player.hb_w + (float)(2*target.hb_w* Math.random()), AbstractDungeon.floorY * 1.5F + (float) (100 * Math.random()), 0, 0, images[0].getWidth(), images[0].getHeight(), (float) (scale * Math.random()), (float) (scale * Math.random()), rotation, 0, 0, images[0].getWidth(), images[0].getHeight(), false, false);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
