package PrismaMod.actions.CardActions.Illya.Poison;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import PrismaMod.actions.sfx.IllyaSFX.CardSFX.NormalSFX.DefendSFX;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class PoisoningPhantasmAction extends AbstractCardAction {

    public float delay;
    public boolean hit;
    public PoisoningPhantasmAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        this.target = p;
        this.sfx = new DefendSFX(p);
        this.delay = this.sfx.getStallTimer();
        this.duration = this.sfx.duration;
        AbstractDungeon.effectsQueue.add(sfx);
        this.hit = false;
        handler.setAnimation(SKILL_ONE);
        //handler.setAnimtion(CHANNEL_ANIM);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            MonsterGroup list = AbstractDungeon.getMonsters();
            holdingCell[] tmpList = new holdingCell[list.monsters.size()];
            int i = 0;
            for (AbstractMonster mon : list.monsters) {
                if (mon.hasPower(PoisonPower.POWER_ID)) {
                    tmpList[i] = new holdingCell(i, mon.getPower(PoisonPower.POWER_ID).amount);
                    //mon.getPower(PoisonPower.POWER_ID).amount = (int)(mon.getPower(PoisonPower.POWER_ID).amount*.75);
                }
                i++;
            }
            int j = 0;
            for (AbstractMonster mon : list.monsters) {
                for (int q = 0; q < tmpList.length; q++) {
                    try {
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new PoisonPower(mon, mon, tmpList[q].numPoison / 4)));
                    } catch (java.lang.NullPointerException e) {
                        DefaultMod.logger.info("There aren't any monsters with poison why would you play this?");
                    }
                }
                j++;
            }
            this.hit = true;
        }
        this.tickDuration();
    }

    private class holdingCell{
        public int numPoison;
        public int location;
        public holdingCell(int location, int numPoison){
            this.location = location;
            this.numPoison = numPoison;
        }
    }
}
