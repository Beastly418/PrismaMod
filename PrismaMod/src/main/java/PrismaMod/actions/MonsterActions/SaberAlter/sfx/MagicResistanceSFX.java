package PrismaMod.actions.MonsterActions.SaberAlter.sfx;

import PrismaMod.actions.MonsterActions.AbstractMonsterSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static PrismaMod.DefaultMod.getModID;

public class MagicResistanceSFX extends AbstractMonsterSFX {

    public AbstractPrismaMonster m;
    public AbstractPlayer target;

    public MagicResistanceSFX(AbstractPrismaMonster m){
        this.m = m;

        this.images.add(new picture("SaberAlter/VFX/VFX_1"));    //<<< NEED TO MAKE THESE FOLDERS STILL DONT FORGET IDIOT!!!!
        //... Do this for each texture needed

        this.duration = this.fadeInTimer + this.stallTimer + this.fadeOutTimer;
        for(picture g: images){
            //UPDATE THE COLORS AND THE LOCATIONS HERE along with scale and rotation
        }
        CardCrawlGame.sound.play(getModID() + "SE_2");  //<<< PLAY THE SOUND EFFECTS HERE
    }

    public void update(){
        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
        } else {
            //Fade in and fade out
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58
                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }
                if (this.stallTimer < endStall && !playedVoice) {
                    //Play the voice lines here


                    playedVoice = true;
                }

                //Fade in each image
                for (picture g : images) {
                    g.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);
                }
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }

                //Fade out each image
                for (picture g : images) {
                    g.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);
                }
            } else {
                this.isDone = true;// 70
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for(picture g: images){
            sb.setColor(g.color);
            sb.draw(g.image, g.x, g.y, 0, 0, g.image.getWidth(), g.image.getHeight(), g.scale, g.scale, g.rotation, 0, 0, g.image.getWidth(), g.image.getHeight(), false, false);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public float getStallTimer() {
        return stallTimer;
    }

    @Override
    public float getEndStall() {
        return endStall;
    }
}
