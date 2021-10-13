package PrismaMod.actions.MonsterActions.SaberAlter;

import PrismaMod.actions.MonsterActions.AbstractMonsterAction;
import PrismaMod.actions.MonsterActions.SaberAlter.sfx.ResetBuffsSFX;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.bossMonsters.SaberAlter;
import PrismaMod.powers.monsters.MemoriesOfTheDragonPower;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Iterator;

@Deprecated
public class ResetBuffs extends AbstractMonsterAction {

    public int damage;
    public DamageInfo.DamageType damageType;
    public ResetBuffs(SaberAlter m, AbstractPlayer target, DamageInfo attack){
        this.m = m;
        this.handler = getHandler(m);
        //this.sfx = new ResetBuffsSFX(m, target);
        //AbstractDungeon.effectsQueue.add(sfx);
        this.target = target;
        this.damage = attack.output;
        this.damageType = attack.type;
        this.duration = 1.15f;//this.sfx.duration;
        this.delay = 1.15f/2;//this.sfx.getStallTimer();// - this.sfx.getEndStall()/4F;
        this.hit = false;
        if(damage < 15) {
            handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.ATTACK);
        } else {
            handler.setAnimation(AbstractPrismaMonster.MonsterAnimationEnums.BIG_ATTACK);
        }
    }

    @Override
    public void update() {
        this.delay -= Gdx.graphics.getDeltaTime();
        if(this.delay <= 0 && !hit){
            ArrayList<AbstractPower> al = this.m.powers;
            Iterator itr = al.iterator();
            while (itr.hasNext())
            {
                AbstractPower x = (AbstractPower)itr.next();
                if (!x.ID.equalsIgnoreCase(MemoriesOfTheDragonPower.POWER_ID))
                    itr.remove();
            }
            this.hit = true;
        }
        this.tickDuration();
    }
}
