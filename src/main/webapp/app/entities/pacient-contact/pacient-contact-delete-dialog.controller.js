(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientContactDeleteController',PacientContactDeleteController);

    PacientContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientContact'];

    function PacientContactDeleteController($uibModalInstance, entity, PacientContact) {
        var vm = this;

        vm.pacientContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
