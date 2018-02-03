(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocioeconomicLevelDeleteController',SocioeconomicLevelDeleteController);

    SocioeconomicLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'SocioeconomicLevel'];

    function SocioeconomicLevelDeleteController($uibModalInstance, entity, SocioeconomicLevel) {
        var vm = this;

        vm.socioeconomicLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SocioeconomicLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
