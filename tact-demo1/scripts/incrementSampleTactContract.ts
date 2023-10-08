import { Address, toNano } from 'ton-core';
import { SampleTactContract } from '../wrappers/SampleTactContract';
import { NetworkProvider, sleep } from '@ton-community/blueprint';

export async function run(provider: NetworkProvider, args: string[]) {
    const ui = provider.ui();
    const sampleTactContractAddress = Address.parse("EQCXnAzKe_0haAW2g4ez5wxMu4OaKmOPDrd529PcuVC-YOJ1");

    if (!(await provider.isContractDeployed(sampleTactContractAddress))) {
        ui.write(`Error: Contract at address ${sampleTactContractAddress} is not deployed!`);
        return;
    }

    const sampleTactContract = provider.open(SampleTactContract.fromAddress(sampleTactContractAddress));

    const counterBefore = await sampleTactContract.getCounter();

    await sampleTactContract.send(
        provider.sender(),
        {
            value: toNano('0.05'),
        },
        {
            $$type: 'Add',
            amount: 1n,
        }
    );

    ui.write('Waiting for counter to increase...');

    let counterAfter = await sampleTactContract.getCounter();
    let attempt = 1;
    while (counterAfter === counterBefore) {
        ui.setActionPrompt(`Attempt ${attempt}`);
        await sleep(2000);
        counterAfter = await sampleTactContract.getCounter();
        attempt++;
    }

    ui.clearActionPrompt();
    ui.write('Counter increased successfully!');
}
