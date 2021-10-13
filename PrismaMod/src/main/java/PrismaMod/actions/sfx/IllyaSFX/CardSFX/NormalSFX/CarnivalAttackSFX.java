package PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

import static PrismaMod.DefaultMod.getModID;
import static PrismaMod.DefaultMod.makeSFXPath;

public class CarnivalAttackSFX extends AbstractSFX {
    private Texture img;

    private Texture[] images;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;

    private AbstractCreature target;

    public CarnivalAttackSFX(AbstractPlayer p, AbstractCreature target){
        this.images = new Texture[4];
        this.img = this.images[0] = TextureLoader.getTexture(makeSFXPath("Scythe.png"));
        this.color = new Color(1.0F, 0.0F, 1.0F, 0.0F);// 24
        this.scale = .5F * Settings.scale;// 29
        this.target = target;
        this.rotation = 1.0F;// 30
        this.target = target;
        if(Settings.FAST_MODE){
            this.fadeInTimer = this.fadeInTimer/2;
            this.fadeOutTimer = this.fadeOutTimer/2;
            this.stallTimer = this.stallTimer/2;
            this.endStall = this.stallTimer/2;
        }
        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        CardCrawlGame.sound.play(getModID() + "SE_1");
    }

    @Override
    public void update() {
        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
            if (this.stallTimer < endStall && !playedVoice){
                double rand = Math.random()*10;
                if (rand < 2) {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_1");
                } else if (rand >= 2 && rand < 4) {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_2");
                } else {
                    CardCrawlGame.sound.play(getModID() + "ATTACK_3");
                }
                playedVoice = true;
            }
        } else {
            if(!playedSound) {
                CardCrawlGame.sound.play(getModID() + "SCYTHE");
                playedSound = true;
            }
            //Fade in and fade out
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58
                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }

                rotation = rotation - 2F;
                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }

                rotation = rotation - 2F;
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
        sb.draw(img, target.hb.cX - target.hb_w/2F, target.hb.cY, 0, 0, img.getWidth(), img.getHeight(), scale, scale, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
    }

    @Override
    public void dispose() {

    }
}
