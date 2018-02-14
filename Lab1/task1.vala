enum Habitat { HOME, FOREST, SAVANNA, CIRCUS, ZOO, STEPPE;
	public string to_string() {
		string value = null;
		switch (this) {
			case ZOO: value = "zoo"; break;
			case HOME: value = "home"; break;
			case FOREST: value = "forest"; break;
			case CIRCUS: value = "circus"; break;
			case STEPPE: value = "steppe"; break;
			case SAVANNA: value = "savanna"; break;
		}
		return value;
	}
}
enum Breed { DOMESTIC, TIGER, LION, PUMA, JAGUAR, LEOPARD;
	public string to_string() {
		string value = null;
		switch (this) {
			case DOMESTIC: value = "cat"; break;
			case TIGER: value = "tiger"; break;
			case LION: value = "lion"; break;
			case PUMA: value = "puma"; break;
			case JAGUAR: value = "jaguar"; break;
			case LEOPARD: value = "leopard"; break;
		}
		return value;
	}
}

namespace Task1 {
	/*
	 * Component
	 */
	abstract class Animal : Object {
		protected double height;
		protected double weight;
		protected int age;

		public abstract void show_info();
		public abstract void sleep();
		public abstract void eat();
	}
	/*
	 * ConcreteComponent
	 */
	class AnimalObject : Animal {
		public AnimalObject(double height, double weight, int age) {
			this.height = height;
			this.weight = weight;
			this.age = age;
		}
		public override void show_info() {
			if(this.height > 0 && this.height < 3) stdout.printf("Height: %.2f meters\n", this.height);
			if(this.weight > 0 && this.weight < 10) stdout.printf("Weight: %.2f kilograms\n", this.weight);
			if(this.age > 0 && this.age < 20) stdout.printf("Age: %d years\n", this.age);
		}
		public override void sleep() {
			stdout.printf("Animal is sleeping ...\n");
		}
		public override void eat() {
			stdout.printf("Animal is eating ...\n");
		}
	}
	/*
	 * Decorator
	 */
	abstract class Felidae : Animal {
		protected Animal animal;
		protected string name;
		protected Habitat habitat;
		protected Breed breed;

		public override void show_info() {
			if((this.name != null) && (this.habitat == Habitat.HOME || this.habitat == Habitat.CIRCUS || this.habitat == Habitat.ZOO)) stdout.printf("Name: %s\n", this.name);
			this.animal.show_info();
			stdout.printf("Habitat: %s\n", this.habitat.to_string());
			stdout.printf("Breed: %s\n", this.breed.to_string());
		}
		public override void sleep() {
			stdout.printf("%s is sleeping ...\n", this.breed.to_string());
		}
		public override void eat() {
			stdout.printf("%s is eating ...\n", this.breed.to_string());
		}
		public abstract void play();
		public abstract void tricks();
		public abstract void cage();
		public abstract void hunt();
	}
	/*
	 * ConcreteDecorator
	 */
	class Domestic : Felidae {
		public Domestic(Animal animal, string name) {
			this.name = name;
			this.animal = animal;
			this.habitat = Habitat.HOME;
			this.breed = Breed.DOMESTIC;
		}
		public override void play() {
			stdout.printf("%s %s is playing ...\n", this.breed.to_string(), this.name);
		}
		public override void tricks() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void cage() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void hunt() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
	}
	class Tiger : Felidae {
		public Tiger(Animal animal, string name) {
			this.animal = animal;
			this.name = name;
			this.habitat = Habitat.CIRCUS;
			this.breed = Breed.TIGER;
		}
		public override void play() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void tricks() {
			stdout.printf("%s %s is showing tricks ...\n", this.breed.to_string(), this.name);
		}
		public override void cage() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void hunt() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
	}
	class Lion : Felidae {
		public Lion(Animal animal, string name) {
			this.animal = animal;
			this.name = name;
			this.habitat = Habitat.ZOO;
			this.breed = Breed.LION;
		}
		public override void play() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void tricks() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void cage() {
			stdout.printf("%s %s is sitting in cage ...\n", this.breed.to_string(), this.name);
		}
		public override void hunt() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
	}
	class Puma : Felidae {
		public Puma(Animal animal) {
			this.name = null;
			this.animal = animal;
			this.habitat = Habitat.STEPPE;
			this.breed = Breed.PUMA;
		}
		public override void play() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void tricks() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void cage() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void hunt() {
			stdout.printf("%s is hunting ...\n", this.breed.to_string());
		}
	}
	class Jaguar : Felidae {
		public Jaguar(Animal animal) {
			this.name = null;
			this.animal = animal;
			this.habitat = Habitat.SAVANNA;
			this.breed = Breed.JAGUAR;
		}
		public override void play() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void tricks() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void cage() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void hunt() {
			stdout.printf("%s is hunting ...\n", this.breed.to_string());
		}
	}
	class Leopard : Felidae {
		public Leopard(Animal animal) {
			this.name = null;
			this.animal = animal;
			this.habitat = Habitat.FOREST;
			this.breed = Breed.LEOPARD;
		}
		public override void play() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void tricks() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void cage() {
			stdout.printf("%s cannot do this ...\n", this.breed.to_string());
		}
		public override void hunt() {
			stdout.printf("%s is hunting ...\n", this.breed.to_string());
		}
	}
	static int main(string[] args) {
		Animal animal = new AnimalObject(1.2, 4.5, 6);
		animal.eat();
		Felidae domestic = new Domestic(animal, "sharik");
		domestic.show_info();
		domestic.eat();
		return 0;
	}
}
