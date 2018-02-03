(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medic_InformationDeleteController',Medic_InformationDeleteController);

    Medic_InformationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medic_Information'];

    function Medic_InformationDeleteController($uibModalInstance, entity, Medic_Information) {
        var vm = this;

        vm.medic_Information = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medic_Information.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
