package PrismaMod.actions.sfx.IllyaSFX.CardSFX.PoisonSFX;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.DefaultMod.getModID;
import static PrismaMod.DefaultMod.makeSFXPath;

public class FeedSFX extends AbstractSFX {
    private final Texture img;// = TextureLoader.getTexture(makeOrbPath("Testament_orb.png"));
    private float x;
    private float y;

    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private AbstractCreature target;

    private class picture {
        public Texture image;
        public float x;
        public float y;
        public Color color;
        public picture(Texture image, float x, float y, Color color){
            this.x = x;
            this.y = y;
            this.image = image;
            this.color = color;
        }
    }

    public picture pic;

    public FeedSFX(AbstractCreature target){
        this.img = TextureLoader.getTexture(makeSFXPath("vampFangs.png"));// 23
        float rand = (float)(Math.random()*.5);
        this.color = new Color(1, rand, rand, 0);// 24
        this.scale = 1.2F * Settings.scale;// 29
        this.target = target;
        this.rotation = MathUtils.random(-1.0F, 1.0F);// 30
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        this.x = target.drawX - (img.getWidth()*scale)/2;
        this.y = AbstractDungeon.floorY;
        this.pic = new picture(img, x, y, color);
        CardCrawlGame.sound.play(getModID() + "SE_1");
    }

    @Override
    public void update() {
        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
            if (this.stallTimer < endStall && !playedVoice){
                double rand = Math.random()*10;
                if (rand < 5) {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_1");
                } else if (rand >= 5 && rand < 8) {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_2");
                } else {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_3");
                }
                playedVoice = true;
            }
        } else {
            this.rotation += MathUtils.random(-0.5F, 0.5F);// 53
            this.scale += 0.005F * Settings.scale;// 54
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58
                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }

                pic.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }

                pic.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);// 68
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
        sb.setColor(pic.color);
        sb.draw(pic.image, pic.x, pic.y, 0, 0, pic.image.getWidth(), pic.image.getHeight(), scale, scale, rotation, 0, 0, pic.image.getWidth(), pic.image.getHeight(), false, false);
    }

    @Override
    public void dispose() {

    }
}
