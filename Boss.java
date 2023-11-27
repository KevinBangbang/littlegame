package AdventureModel;

import java.util.List;

public class Boss extends BasicMonster {

    public Boss(int health, int attack, int defense, List<SpecialAbility> specialAbilities) {
        super(health, attack, defense, specialAbilities);
    }

    @Override
    public void interactWithPlayer(Player player) {
        // Specific interaction logic for Boss
    }

    // Boss-specific methods...
}
