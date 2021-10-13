package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_TWO;

public class RapidFireAction extends AbstractCardAction {

    public boolean upgraded;
    public float delay;
    public boolean hit;
    public int damage;
    public RapidFireAction(AbstractPlayer p, AbstractCreature target, boolean upgraded, int damage){
        this.p = p;
        this.handler = getHandler(p);
        this.target = target;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.upgraded = upgraded;
        this.damage = damage;
        handler.setAnimation(ATTACK_TWO);
        //handler.setAnimtion(SWIPE_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            int numOrbs = p.maxOrbs;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs * 2; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
