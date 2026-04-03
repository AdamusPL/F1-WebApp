import GrandPrixChooser from "../components/GrandPrixChooser";
import { useGrandPrix } from "../components/GrandPrixProvider";
import PredictionFields from "../components/PredictionFields";
import SessionChooser from "../components/SessionChooser";
import { useSession } from "../components/SessionProvider";

export default function Predict() {

    const { grandPrix } = useGrandPrix();
    const { session } = useSession();

    return (<>
        <h1 className="mt-4">Predict</h1>

        <GrandPrixChooser />

        {grandPrix ?
            <SessionChooser />
            :
            null}

        {session ?
            <PredictionFields />
            :
            null}
    </>);
}