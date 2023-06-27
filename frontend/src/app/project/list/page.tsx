
import Newproject from "./components/Newproject"
import Timeline from './components/Timeline';
import Filter from "./components/Filter";

export default function List(){
  return (
    <div>
        <Newproject />
        <div className="max-w-5xl mx-auto">
          <div className="text-2xl text-left my-2">
            Timeline
          </div>
          <Timeline />
        </div>
        <div className="max-w-5xl mx-auto my-5">
            <div className="text-2xl text-left my-2">
                Filters
            </div>
            <Filter />
        </div>
    </div>
  );
};
