package PrismaMod.actions.CardActions.Illya.Install;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class InstallAvengerAction extends AbstractInstallAction {

    public InstallAvengerAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
    }

    @Override
    public void update() {

        this.tickDuration();
    }
}
