(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientDeleteController',PacientDeleteController);

    PacientDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pacient'];

    function PacientDeleteController($uibModalInstance, entity, Pacient) {
        var vm = this;

        vm.pacient = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pacient.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
