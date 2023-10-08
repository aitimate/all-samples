import { Blockchain, SandboxContract } from '@ton-community/sandbox';
import {Address, toNano} from 'ton-core';
import { SampleTactContract } from '../wrappers/SampleTactContract';
import '@ton-community/test-utils';

describe('SampleTactContract', () => {
    let blockchain: Blockchain;
    let sampleTactContract: SandboxContract<SampleTactContract>;

    beforeEach(async () => {
        blockchain = await Blockchain.create();
        const owner = Address.parse("EQCIEpWqHt0fiLKUfBW--r5FB1Ici3RPu3Qt5SaV51HjGN5D");

        sampleTactContract = blockchain.openContract(await SampleTactContract.fromInit(owner));

        const deployer = await blockchain.treasury('deployer');

        const deployResult = await sampleTactContract.send(
            deployer.getSender(),
            {
                value: toNano('0.05'),
            },
            {
                $$type: 'Deploy',
                queryId: 0n,
            }
        );

        expect(deployResult.transactions).toHaveTransaction({
            from: deployer.address,
            to: sampleTactContract.address,
            deploy: true,
            success: true,
        });
    });

    it('should deploy', async () => {
        // the check is done inside beforeEach
        // blockchain and sampleTactContract are ready to use
    });

    it('should increase counter', async () => {
        const increaseTimes = 3;
        for (let i = 0; i < increaseTimes; i++) {
            console.log(`increase ${i + 1}/${increaseTimes}`);

            const increaser = await blockchain.treasury('increaser' + i);

            const counterBefore = await sampleTactContract.getCounter();

            console.log('counter before increasing', counterBefore);

            const increaseBy = BigInt(Math.floor(Math.random() * 100));

            console.log('increasing by', increaseBy);

            const increaseResult = await sampleTactContract.send(
                increaser.getSender(),
                {
                    value: toNano('0.05'),
                },
                {
                    $$type: 'Add',
                    amount: increaseBy,
                }
            );

            expect(increaseResult.transactions).toHaveTransaction({
                from: increaser.address,
                to: sampleTactContract.address,
                success: true,
            });

            const counterAfter = await sampleTactContract.getCounter();

            console.log('counter after increasing', counterAfter);

            expect(counterAfter).toBe(counterBefore + increaseBy);
        }
    });
});
