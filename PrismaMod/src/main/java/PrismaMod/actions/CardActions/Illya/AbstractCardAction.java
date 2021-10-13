package PrismaMod.actions.CardActions.Illya;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractCardAction extends AbstractGameAction {

    public AbstractPlayer p;
    public Illya.IllyaAnimHandler handler;
    protected AbstractSFX sfx;

    public boolean isIllya(AbstractPlayer player){
        return player instanceof Illya;
    }

    public Illya.IllyaAnimHandler getHandler(AbstractPlayer player) {
        if(isIllya(player)){
            return ((Illya) AbstractDungeon.player).animationHandler;
        }
        return null;
    }



}
