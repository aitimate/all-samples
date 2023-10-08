import "@stdlib/deploy";

message Add {
    amount: Int as uint32;
}

contract SampleTactContract with Deployable {
    owner: Address;
    counter: Int as uint32;

    init(owner: Address) {
        self.owner = owner;
        self.counter = 0;
    }

    receive("increment") {
        self.add(1);
    }

    receive(msg: Add) {
        self.add(msg.amount);
    }

    fun add(v: Int) {
        // Check sender
        let ctx: Context = context();
        require(ctx.sender == self.owner, "Invalid sender");

        // Update counter
        self.counter = (self.counter + v);
    }

    get fun counter(): Int {
        return self.counter;
    }
}
