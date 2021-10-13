package PrismaMod.actions.CardActions.Illya.Misc;

import PrismaMod.actions.CardActions.Illya.AbstractCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static PrismaMod.characters.AbstractPrismaPlayer.PlayerAnimationEnums.SKILL_ONE;

public class ZweiAction extends AbstractCardAction {

    public ZweiAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
        handler.setAnimation(SKILL_ONE);
    }

    @Override
    public void update() {

        this.tickDuration();
    }
}
