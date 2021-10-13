package PrismaMod.actions.CardActions.Chloe;

import PrismaMod.cards.ChloeCards.Normal.ChloeShiv;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
@Deprecated
public class ExhaustCardAction extends AbstractChloeAction {

    //THIS ONLY WORKS FOR A HAND!!!
    //This action will exhaust X amount of cards given to it and then give the player a specified number of 0 cost Mill cards to burn through

    private int numShivs;

    @Deprecated
    public ExhaustCardAction(AbstractPlayer p, int numShivs, String msg, int amt){

        this.numShivs = numShivs;
        this.p = p;
        this.isDone = false;
        AbstractDungeon.handCardSelectScreen.open(msg, amt, true, true);
    }

    @Override
    public void update() {
        if(!this.isDone && !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, p.hand));
                for(int i = 0; i < this.numShivs; i++)
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new ChloeShiv()));
            }
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.isDone = true;
        }
    }
}
