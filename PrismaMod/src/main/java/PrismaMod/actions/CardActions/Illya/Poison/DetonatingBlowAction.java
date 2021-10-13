package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.AttackSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_ONE;

public class DetonatingBlowAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public int damage;
    public DetonatingBlowAction(AbstractPlayer p, AbstractCreature target, int damage){
        this.p = p;
        this.handler = getHandler(p);
        this.sfx = new AttackSFX(p, target);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        this.target = target;
        this.damage = damage;
        handler.setAnimation(ATTACK_ONE);
        //handler.setAnimtion(BLAST_ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            int numPoison;
            if (target.hasPower(PoisonPower.POWER_ID)) {
                AbstractPower pow = target.getPower(PoisonPower.POWER_ID);
                numPoison = pow.amount/4;
                //AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(m, numPoison, DamageInfo.DamageType.HP_LOSS)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
                //target.powers.remove(pow);

                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    try {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new PoisonPower(mon, mon, numPoison)));
                    } catch (Exception e) {
                        DefaultMod.logger.info("There aren't any monsters with poison why would you play this?");
                    }                }
            }
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(target, damage, DamageInfo.DamageType.NORMAL)));//, AbstractGameAction.AttackEffect.NONE));
            this.hit = true;
        }
        this.tickDuration();
    }
}
