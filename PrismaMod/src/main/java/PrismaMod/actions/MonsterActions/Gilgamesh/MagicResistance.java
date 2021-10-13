package PrismaMod.actions.MonsterActions.Gilgamesh;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.Gilgamesh.sfx.MagicResistanceSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.Gilgamesh;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class MagicResistance extends AbstractMonsterAction {

    public int numPlate;
    public MagicResistance(Gilgamesh m, int numPlate){
        this.m = m;
        this.handler = getHandler(m);
        this.sfx = new MagicResistanceSFX(m);
        AbstractDungeon.effectsQueue.add(sfx);
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        this.numPlate = numPlate;
        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.ATTACK);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new PlatedArmorPower(this.m, this.numPlate)));
            this.hit = true;
        }
        this.tickDuration();
    }
}
