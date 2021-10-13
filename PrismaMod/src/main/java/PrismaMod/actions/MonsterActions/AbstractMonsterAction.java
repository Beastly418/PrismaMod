package PrismaMod.actions.MonsterActions;

import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.monsters.AbstractPrismaMonster.PrismaAnimation;
import PrismaMod.monsters.bossMonsters.Gilgamesh;
import PrismaMod.monsters.bossMonsters.Rider;
import PrismaMod.monsters.bossMonsters.SaberAlter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public abstract class AbstractMonsterAction extends AbstractGameAction {

    public PrismaAnimation handler;
    public AbstractMonsterSFX sfx;
    public AbstractPrismaMonster m;
    public float delay;
    public boolean hit;

    public PrismaAnimation getHandler(Gilgamesh monster){
        //if(monster instanceof AbstractPrismaMonster){
        return monster.animHandler;
    }
    public PrismaAnimation getHandler(SaberAlter monster){
        //if(monster instanceof AbstractPrismaMonster){
        return monster.animHandler;
    }
    public PrismaAnimation getHandler(Rider monster){
        //if(monster instanceof AbstractPrismaMonster){
        return monster.animHandler;
    }
}
