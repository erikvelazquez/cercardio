(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientApnpDeleteController',PacientApnpDeleteController);

    PacientApnpDeleteController.$inject = ['$uibModalInstance', 'entity', 'PacientApnp'];

    function PacientApnpDeleteController($uibModalInstance, entity, PacientApnp) {
        var vm = this;

        vm.pacientApnp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PacientApnp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
