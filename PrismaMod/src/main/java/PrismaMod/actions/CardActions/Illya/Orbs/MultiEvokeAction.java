package PrismaMod.actions.CardActions.Illya.Orbs;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.ATTACK_TWO;

public class MultiEvokeAction extends AbstractCardAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private boolean upgraded = false;

    public MultiEvokeAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        if (p instanceof Illya && !enablePlaceholder) {
            this.handler = getHandler(p);
            handler.setAnimation(ATTACK_TWO);
            //handler.setAnimtion(ORB_ATTACK);
        }
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = 0;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            if (!this.upgraded) {
                for (int i = 0; i < effect; i++) {
                    AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
                }
            } else if (this.upgraded) {
                for (int i = 0; i < effect; i++) {
                    AbstractDungeon.actionManager.addToTop(new EvokeWithoutRemovingOrbAction(1));
                }
            }
            if(!this.freeToPlayOnce){
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.tickDuration();

        this.isDone = true;
    }
}
