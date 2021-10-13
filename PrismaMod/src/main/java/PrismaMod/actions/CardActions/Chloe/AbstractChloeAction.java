package PrismaMod.actions.CardActions.Chloe;

import PrismaMod.actions.sfx.AbstractSFX;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Chloe.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractChloeAction extends AbstractGameAction {

    protected AbstractSFX sfx;
    public AbstractPlayer p;
    public AbstractCreature target;

    public boolean isChloe(AbstractPlayer player){
        return player instanceof Chloe;
    }


}
