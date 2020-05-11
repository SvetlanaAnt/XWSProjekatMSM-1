import React from 'react';
import { Button, Modal } from 'react-bootstrap'

const DeleteModalContainer = ({ msg = 'Jeste li sigurni da želite da nastavite brisanje?', ...props }) => {


    return (

        <Modal show={props.show} onHide={() => { props.setShow(false) }}>
            <Modal.Header closeButton>
                <Modal.Title>Brisanje</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>{msg}</p>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={() => { props.setShow(false) }}>Ne</Button>
                <Button variant="danger" onClick={props.onDelete}>Da</Button>
            </Modal.Footer>

        </Modal>
    );
}

export default DeleteModalContainer;
