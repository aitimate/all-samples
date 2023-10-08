import {Address, toNano} from 'ton-core';
import { SampleTactContract } from '../wrappers/SampleTactContract';
import { NetworkProvider } from '@ton-community/blueprint';


export async function run(provider: NetworkProvider) {
    const owner = Address.parse("EQCIEpWqHt0fiLKUfBW--r5FB1Ici3RPu3Qt5SaV51HjGN5D");
    const sampleTactContract = provider.open(await SampleTactContract.fromInit(owner));

    await sampleTactContract.send(
        provider.sender(),
        {
            value: toNano('0.05'),
        },
        {
            $$type: 'Deploy',
            queryId: 0n,
        }
    );

    await provider.waitForDeploy(sampleTactContract.address);

    console.log('Counter', await sampleTactContract.getCounter());
}
