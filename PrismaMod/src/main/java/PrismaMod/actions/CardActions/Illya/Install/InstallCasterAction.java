package PrismaMod.actions.CardActions.Illya.Install;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class InstallCasterAction extends AbstractInstallAction {

    public InstallCasterAction(AbstractPlayer p){
        this.p = p;
        this.handler = getHandler(p);
    }

    @Override
    public void update() {


        this.tickDuration();
    }
}
