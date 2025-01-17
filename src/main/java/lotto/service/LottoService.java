package lotto.service;

import lotto.controller.dto.input.BuyLottosInput;
import lotto.controller.dto.input.DrawLottosInput;
import lotto.controller.dto.output.BuyLottosOutput;
import lotto.controller.dto.output.DrawLottosOutput;
import lotto.domain.lotto.DrawLottosData;
import lotto.domain.lotto.LottoDrawer;
import lotto.domain.lotto.LottoSeller;
import lotto.domain.lotto.Lottos;
import lotto.domain.lotto.WinningLotto;
import lotto.domain.lotto.repository.LottoRepository;
import lotto.domain.money.Wallet;
import lotto.service.mapper.LottoFromDtoMapper;
import lotto.service.mapper.LottoToDtoMapper;
import lotto.service.mapper.WalletFromDtoMapper;

/**
 * 여러 도메인 객체의 흐름을 관장하는 Service 계층입니다.
 * <p>
 * Controller로부터 받아온 Dto를 Domain 객체로 변환하고,
 * <p>
 * 각 도메인 객체에 비즈니스 로직을 위임하고,
 * <p>
 * Storage 저장 로직은 Repository에 위임합니다.
 * <p>
 * Controller가 입력 처리 출력에 대한 모든 흐름을 제어한다면,
 * <p>
 * Service는 비즈니스 로직에 대한 모든 흐름을 제어하는 계층이라고 보여집니다.
 * <p>
 * 어떤 어플리케이션이더라도 단순하게 입출력 로직이 전부인 Controller, Repository과 달리,
 * <p>
 * 비즈니스 로직은 어플리케이션마다 매우 다양한 모습을 띌 수 있으며,
 * <p>
 * 복잡해질수록 Controller, Repository에 비해 비대해질 것이라고 생각되기 때문에,
 * <p>
 * 이를 Domain이라는 여러 객체로 분리한 것으로 이해했습니다.
 * <p>
 * 결국 어느 계층이 비대해지고 너무 역할이 많아지면 그게 어떤 영역일지라도,
 * <p>
 * 세부적으로 나누는 방식으로 접근하는 것 같습니다.
 * <p>
 * 그동안 공부하면서 유사한 사례들을 계속해서 봐왔기 때문에 비교해서 생각하면 좀 더 이해가 수월합니다.
 */
public final class LottoService {
    private final LottoSeller lottoSeller;
    private final LottoRepository lottoRepository;

    public LottoService(
            final LottoSeller lottoSeller,
            final LottoRepository lottoRepository
    ) {
        this.lottoSeller = lottoSeller;
        this.lottoRepository = lottoRepository;
    }

    /**
     * 로또 구매 입력에 대한 Dto를 받아서 구매 로직을 처리하고, 응답을 Dto로 변환 및 반환
     */
    public BuyLottosOutput buyLottos(final BuyLottosInput dto) {
        final Wallet wallet = WalletFromDtoMapper.from(dto);
        final Lottos boughtLottos = lottoSeller.buyUntilOutOfMoney(wallet);
        final Lottos clonedLottos = boughtLottos.clone();
        lottoRepository.saveAll(boughtLottos);

        return LottoToDtoMapper.from(clonedLottos);
    }

    /**
     * 로또 추첨에 대한 입력 Dto를 받아서 추첨 로직을 처리하고, 응답을 Dto로 변환 및 반환
     */
    public DrawLottosOutput drawLottos(final DrawLottosInput dto) {
        final WinningLotto winningLotto = LottoFromDtoMapper.from(dto);
        final LottoDrawer lottoDrawer = new LottoDrawer(winningLotto);
        final Lottos lottos = new Lottos(lottoRepository.findAll());

        final DrawLottosData drawLottosData = lottoDrawer.draw(lottos);
        return drawLottosData.toDrawLottosOutput();
    }
}
