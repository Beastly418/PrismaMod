package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.MiscSFX.AliceSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class AstolfoAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int value;

    public AstolfoAction(AbstractPlayer p, int value){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new AliceSFX(p);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.value = value;
        //handler.setAnimtion(CHANNEL_ANIM);
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            int rand = (int) (Math.random()*5);
            switch(rand) {
                case 0:
                    AbstractDungeon.actionManager.addToTop(new HealAction(p, p, value));
                    break;
                case 1:
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, value)));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, value)));
                    break;
                case 3:
                    p.increaseMaxHp(value, true);
                    break;
                case 4:
                    p.gainGold(value * 10);
                    break;
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}





