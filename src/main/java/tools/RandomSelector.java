package tools;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class RandomSelector<Elt> implements Collector<Elt, RandomSelector.Choice<Elt>, Optional<Elt>> {

  private static final Random gen = new Random();

  @Override
  public Supplier<Choice<Elt>> supplier() {
    return Choice::new;
  }

  @Override
  public BiConsumer<Choice<Elt>, Elt> accumulator() {
    return Choice::accept;
 }

  @Override
  public BinaryOperator<Choice<Elt>> combiner() {
    return Choice::accept;
  }

  @Override
  public Function<Choice<Elt>, Optional<Elt>> finisher() {
    return Choice::getElement;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of(Characteristics.UNORDERED);
  }

  static class Choice<Elt> {
    int chances;
    Elt elt;


    Choice() {
      chances = 0;
      elt = null;
    }

    Choice(Elt elt, int chances) {
      this.elt = elt;
      this.chances = chances;
    }

    Choice<Elt> accept(Choice<Elt> choice) {
      int total = chances + choice.chances;
      return (total == 0) ? new Choice<>():
             new Choice<>((gen.nextInt(total) >= chances) ?
                          choice.elt : this.elt, total
             );
    }

     void accept(Elt elt) {
      int total = chances + 1;
      if (chances == 0 || gen.nextInt(total) >= chances) {
        this.elt = elt;
      }
      chances = total;
     };

     Optional<Elt> getElement() {
       return chances == 0 ? Optional.empty() : Optional.of(elt);
     }
  }




}
