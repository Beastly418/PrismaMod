package PrismaMod.actions.CardActions.Illya.Install;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class InstallRulerAction extends AbstractInstallAction {

    public InstallRulerAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
    }

    @Override
    public void update() {

        this.tickDuration();
    }
}
