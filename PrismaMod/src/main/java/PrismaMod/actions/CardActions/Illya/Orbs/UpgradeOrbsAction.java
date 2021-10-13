package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import PrismaMod.powers.UpgradeOrbsPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class UpgradeOrbsAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public UpgradeOrbsAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(ORB_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new UpgradeOrbsPower(p, p, 1)));//,1, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1)));//,1, AbstractGameAction.AttackEffect.NONE));
            this.hit = true;
        }
        this.tickDuration();
    }
}
