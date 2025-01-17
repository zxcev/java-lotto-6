package lotto.view.component.output;

import java.util.List;
import lotto.controller.dto.output.BuyLottosOutput;
import lotto.view.component.Component;

public final class LottoNumbersComponent implements Component {

    private final BuyLottosOutput data;

    public LottoNumbersComponent(final BuyLottosOutput data) {
        this.data = data;
    }

    @Override
    public String render() {
        final List<String> rendered = data.lottos().stream()
                .map(LottoComponent::new)
                .map(LottoComponent::render)
                .toList();

        return String.join("\n", rendered);
    }

}
