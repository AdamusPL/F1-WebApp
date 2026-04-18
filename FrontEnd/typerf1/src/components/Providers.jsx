import { CookiesProvider } from "react-cookie";
import { GrandPrixProvider } from "./GrandPrixProvider";
import { ParticipantProvider } from "./ParticipantProvider";
import { PredictionProvider } from "./PredictionProvider";
import { SessionProvider } from "./SessionProvider";
import { YearProvider } from "./YearProvider";

const Providers = ({ children }) => {
  return (
    <PredictionProvider>
      <SessionProvider>
        <GrandPrixProvider>
          <ParticipantProvider>
            <YearProvider>
              <CookiesProvider>
                {children}
              </CookiesProvider>
            </YearProvider>
          </ParticipantProvider>
        </GrandPrixProvider>
      </SessionProvider>
    </PredictionProvider>
  );
};

export default Providers;