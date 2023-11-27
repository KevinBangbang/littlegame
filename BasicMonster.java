package AdventureModel;

import java.util.List;

public class BasicMonster implements Monster {
    protected int health;
    protected int attack;
    protected int defense;
    protected List<SpecialAbility> specialAbilities;

    public BasicMonster(int health, int attack, int defense, List<SpecialAbility> specialAbilities) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAbilities = specialAbilities;
    }

    @Override
    public void interactWithPlayer(Player player) {
        // Default interaction logic, can be overridden by subclasses
    }

    // Common methods like takeDamage(), performAttack(), etc.
}
