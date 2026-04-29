type Props = {
    cinemas: Cinema[];
};

export const CinemaTable = ({ cinemas }: Props) => {
    return (
        <div className="overflow-x-auto mb-10">
            <table className="min-w-full border border-gray-300">
                <thead className="bg-gray-100">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>City</th>
                    <th>Address</th>
                    <th>Posters</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                {cinemas.map((cinema, index) => (
                    <tr key={cinema.id}>
                        <td>{index + 1}</td>
                        <td>{cinema.name}</td>
                        <td>{cinema.city}</td>
                        <td>{cinema.address}</td>
                        <td>{cinema.total}/{cinema.active}</td>
                        <td>...</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};