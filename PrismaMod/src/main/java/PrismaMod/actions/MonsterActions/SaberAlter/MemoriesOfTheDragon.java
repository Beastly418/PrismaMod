package PrismaMod.actions.MonsterActions.SaberAlter;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.SaberAlter.sfx.MemoriesOfTheDragonSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.SaberAlter;
import PrismaMod.powers.monsters.MemoriesOfTheDragonPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MemoriesOfTheDragon extends AbstractMonsterAction {

    public int damage;
    public DamageInfo.DamageType damageType;
    public MemoriesOfTheDragon(SaberAlter m){
        this.m = m;
        this.handler = getHandler(m);
        this.sfx = new MemoriesOfTheDragonSFX(m);
        AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.duration = this.sfx.duration;
        this.delay = this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;

        handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.CHANNEL);
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new MemoriesOfTheDragonPower(this.m, this.m, 1)));
            this.hit = true;
        }
        this.tickDuration();
    }
}
