(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicDeleteController',MedicDeleteController);

    MedicDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medic'];

    function MedicDeleteController($uibModalInstance, entity, Medic) {
        var vm = this;

        vm.medic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
